
DELETE FROM sh_alipay
WHERE
    id IN (SELECT
        id
    FROM
        (SELECT
            id
        FROM
            sh_alipay

        WHERE
            trade_no IN (SELECT
                trade_no
            FROM
                sh_alipay
            GROUP BY trade_no
            HAVING COUNT(*) > 1)
            AND (id NOT IN (SELECT
                MAX(id) id
            FROM
                sh_alipay
            GROUP BY trade_no
            HAVING COUNT(*) > 1))) a);

INSERT INTO sh_jnl_alipay
	(trade_no,
	notify_type,
	notify_id,
	sign_type,
	sign,
	notify_time,
	out_trade_no,
	subject,
	payment_type,
	trade_status,
	seller_id,
	seller_email,
	buyer_id,
	buyer_email,
	total_fee,
	quantity,
	price,
	body,
	gmt_create,
	gmt_payment,
	is_total_fee_adjust,
	use_coupon,
	discount,
	refund_status,
	gmt_refund)
SELECT a.trade_no,
    a.notify_type,
    a.notify_id,
    a.sign_type,
    a.sign,
    a.notify_time,
    b.buy_order_id,
    a.subject,
    a.payment_type,
    a.trade_status,
    a.seller_id,
    a.seller_email,
    a.buyer_id,
    a.buyer_email,
    a.total_fee,
    a.quantity,
    a.price,
    a.body,
    a.gmt_create,
    a.gmt_payment,
    a.is_total_fee_adjust,
    a.use_coupon,
    a.discount,
    a.refund_status,
    a.gmt_refund
    from sh_alipay a
left join sh_alipay_period b on a.id = b.alipay_id;


INSERT INTO sh_jnl_trans
(jnl_no,
user_id,
trans_code,
jnl_status,
pay_type,
recharge_channel,
amount,

create_time,
update_time)
SELECT
    id,
    sh_user_id,
    'recharge',
    CASE
        WHEN payment_status = '0' THEN '0'
        WHEN '1' THEN '1'
    END,
    1,
    payment_type,
    total_fee,
    create_time,
    create_time
FROM
    sh_recharge