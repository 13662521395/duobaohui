<?php
/**
 * User: guoshijie
 * Date: 15/11/20
 * Time: 上午11:15
 */

namespace Laravel\Service;
use Illuminate\Support\Facades\Session;
use Laravel\Model\ShopModel;


class ShopService {

    public function getShopShareByDate($date){
        return $this->tongJi($date,'byDate');
    }

    /**
     * 记录每天商户推广总结果
     * @param $date
     * @return array|null
     */
    public function addShopShareHistory($date){
        $shopModel = new ShopModel();
        $all_array = $this->tongJi($date,'sum');
        $id = $shopModel->addShopShareHistory($all_array);
        return $id;
    }

    /**
     * 商户推广首页日报
     */
    public function getDailyReport($date){
        return $this->tongJi($date,'byDate');
    }

    /**
     * @param $date
     * @param $flag
     * @return array|null
     */
    public function tongJi($date,$flag) {
        $shopModel = new ShopModel();
        $shopList = $shopModel->getShopList2();
        if($shopList){
            $shop_array = array();
            $avg_customer_sum = 0;

            $scan_sum = 0;
            $scan_sum_android=0;
            $scan_sum_ios=0;

            $avg_scan_sum = 0;

            $red_packet_sum = 0;
            $red_packet_android=0;
            $red_packet_ios=0;

            $real_sum = 0;
            $avg_real_sum = 0;
            $scene_one_sum = 0;
            $scene_two_sum = 0;
            $scene_three_sum = 0;
            $scene_four_sum = 0;

            $scene_one_scan=0;
            $scene_two_scan=0;
            $scene_three_scan=0;
            $scene_four_scan=0;

            $duobao_sum=0;

            foreach($shopList as $item){
                $day_array = array();
                $avg_customer_num = $item->avg_customer_num;

                $todayResVal=$shopModel->getShopShareByDate(date("Y-m-d",strtotime("$date +1 day")),$item->id);
                $yesterdayResVal = $shopModel->getShopShareByDate($date,$item->id);

                if($todayResVal && $yesterdayResVal){
                    $todayRes = $todayResVal[0];
                    $yesterdayRes = $yesterdayResVal[0];

                    //单一商家计算begin
                    $day_array['date'] = $date;
                    $day_array['shop_id'] = $item->id;
                    $day_array['head_name'] = $item->head_name;
                    $day_array['branch_name'] = $item->branch_name;
                    $day_array['avg_customer_sum'] = $avg_customer_num;

                    //扫码
                    $day_array['scan_sum'] = $todayRes->scan_sum - $yesterdayRes->scan_sum;
                    $day_array['scan_sum_android'] = $todayRes->scan_sum_android - $yesterdayRes->scan_sum_android;
                    $day_array['scan_sum_ios'] = $todayRes->scan_sum_ios - $yesterdayRes->scan_sum_ios;

                    $day_array['avg_scan_sum'] = $todayRes->avg_scan_sum;

                    //领红包
                    $day_array['scan_num'] = $todayRes->scan_num - $yesterdayRes->scan_num;
                    $day_array['red_packet_android'] = $todayRes->red_packet_android - $yesterdayRes->red_packet_android;
                    $day_array['red_packet_ios'] = $todayRes->red_packet_ios - $yesterdayRes->red_packet_ios;

                    $percent = $avg_customer_num!=0?round($day_array['scan_num']/$avg_customer_num*100 ,2):0.00;
                    $day_array['percent_scan'] = $percent.'%';
                    $day_array['real_sum'] = $todayRes->real_num - $yesterdayRes->real_num;
                    $day_array['avg_real_sum'] = $todayRes->avg_real_num;
                    $day_array['scene_one'] = $todayRes->scene_one - $yesterdayRes->scene_one;
                    $day_array['scene_two'] = $todayRes->scene_two - $yesterdayRes->scene_two;
                    $day_array['scene_three'] = $todayRes->scene_three - $yesterdayRes->scene_three;
                    $day_array['scene_four'] = $todayRes->scene_four - $yesterdayRes->scene_four;
                    $day_array['scene_one_scan'] = $todayRes->scene_one_scan - $yesterdayRes->scene_one_scan;
                    $day_array['scene_two_scan'] = $todayRes->scene_two_scan - $yesterdayRes->scene_two_scan;
                    $day_array['scene_three_scan'] = $todayRes->scene_three_scan - $yesterdayRes->scene_three_scan;
                    $day_array['scene_four_scan'] = $todayRes->scene_four_scan - $yesterdayRes->scene_four_scan;

                    $turn_user=$day_array['scan_sum']!=0?round($day_array['real_sum']/$day_array['scan_sum']*100,2):0.00;
                    $day_array['percent']=$turn_user.'%';

                    //夺宝人次
                    $day_array['duobao_sum'] = $todayRes->duobao_sum - $yesterdayRes->duobao_sum;

                    //扫码人数/日均客流量
                    $scanSum_divide_avgCustomerSum=$day_array['avg_customer_sum']!=0?round($day_array['scan_sum']/$day_array['avg_customer_sum']*100,2):0.00;
                    $day_array['scanSum_divide_avgCustomerSum']=$scanSum_divide_avgCustomerSum.'%';

                    //领红包人数/扫码人数
                    $redSum_divide_scanSum=$day_array['scan_sum']!=0?round($day_array['scan_num']/$day_array['scan_sum']*100,2):0.00;
                    $day_array['redSum_divide_scanSum']=$redSum_divide_scanSum.'%';

                    //注册人数/领红包人数
                    $registerSum_divide_redSum=$day_array['scan_num']!=0?round($day_array['real_sum']/$day_array['scan_num']*100,2):0.00;
                    $day_array['registerSum_divide_redSum']=$registerSum_divide_redSum.'%';

                    //夺宝人次/注册人数
                    $dbSum_divide_registerSum=$day_array['real_sum']!=0?round($day_array['duobao_sum']/$day_array['real_sum']*100,2):0.00;
                    $day_array['dbSum_divide_registerSum']=$dbSum_divide_registerSum;

                    //单一商家计算end


                    //统计begin
                    $avg_customer_sum += $avg_customer_num;

                    $scan_sum += $day_array['scan_sum'];
                    $scan_sum_android += $day_array['scan_sum_android'];
                    $scan_sum_ios += $day_array['scan_sum_ios'];

                    $avg_scan_sum += $day_array['avg_scan_sum'];

                    $red_packet_sum += $day_array['scan_num'];
                    $red_packet_android += $day_array['red_packet_android'];
                    $red_packet_ios += $day_array['red_packet_ios'];

                    $real_sum += $day_array['real_sum'];
                    $avg_real_sum += $day_array['avg_real_sum'];

                    $scene_one_sum += $day_array['scene_one'];
                    $scene_two_sum += $day_array['scene_two'];
                    $scene_three_sum += $day_array['scene_three'];
                    $scene_four_sum += $day_array['scene_four'];

                    $scene_one_scan += $day_array['scene_one_scan'];
                    $scene_two_scan += $day_array['scene_two_scan'];
                    $scene_three_scan += $day_array['scene_three_scan'];
                    $scene_four_scan += $day_array['scene_four_scan'];

                    $duobao_sum += $day_array['duobao_sum'];
                    //统计end

                    $shop_array[] = (object)$day_array;
                }
            }

            $all_array = array();
            $all_array['date'] = $date;
            $all_array['shop_id'] = count($shop_array) > 0?'-1':'-2';
            $all_array['head_name'] = '共计';
            $all_array['branch_name'] = '';
            $all_array['avg_customer_sum'] = $avg_customer_sum;

            $all_array['scan_sum'] = $scan_sum;
            $all_array['scan_sum_android'] = $scan_sum_android;
            $all_array['scan_sum_ios'] = $scan_sum_ios;

            $all_array['avg_scan_sum'] = $avg_scan_sum;

            $all_array['red_packet_sum'] = $red_packet_sum;
            $all_array['red_packet_android'] = $red_packet_android;
            $all_array['red_packet_ios'] = $red_packet_ios;

            $percent_sum=$avg_customer_sum!=0?round($red_packet_sum/$avg_customer_sum*100,2):0.00;
            $all_array['percent_scan']=$percent_sum.'%';
            $all_array['real_sum']=$real_sum;
            $all_array['avg_real_sum']=$avg_real_sum;

            $all_array['scene_one']=$scene_one_sum;
            $all_array['scene_two']=$scene_two_sum;
            $all_array['scene_three']=$scene_three_sum;
            $all_array['scene_four']=$scene_four_sum;

            $all_array['scene_one_scan']=$scene_one_scan;
            $all_array['scene_two_scan']=$scene_two_scan;
            $all_array['scene_three_scan']=$scene_three_scan;
            $all_array['scene_four_scan']=$scene_four_scan;

            $turn_user_sum=$all_array['scan_sum']!=0?round($all_array['real_sum']/$all_array['scan_sum']*100 ,2):0.00;
            $all_array['percent']=$turn_user_sum.'%';

            $all_array['duobao_sum']=$duobao_sum;

            if($flag == 'sum'){
                return $all_array;
            } else {
                $shop_array[] = (object)$all_array;
                return $shop_array;
            }
        }
        return null;
    }

    /**
     * 查询某段时间内的商户推广成果
     * @param $beginDate
     * @param $endDate
     * @return array|null
     */
    public function getShopShareBetweenDate($beginDate,$endDate) {
        $shopModel = new ShopModel();
        $shopList = $shopModel->getShopList2();
        if($shopList){
            $shop_array = array();
            foreach($shopList as $item){
                $day_array = array();
                $todayResVal=$shopModel->getShopShareByDate(date("Y-m-d",strtotime("$endDate +1 day")),$item->id);
                $yesterdayResVal = $shopModel->getShopShareByDate($beginDate,$item->id);

                if($todayResVal && $yesterdayResVal){
                    $todayRes = $todayResVal[0];
                    $yesterdayRes = $yesterdayResVal[0];
                    $day_array['sh_shop_id'] = $item->id;
                    $day_array['head_name'] = $item->head_name;
                    $day_array['branch_name'] = $item->branch_name;
                    $day_array['days'] = $todayRes->days - $yesterdayRes->days;

                    $day_array['scan_sum'] = $todayRes->scan_sum - $yesterdayRes->scan_sum;
                    $day_array['scan_sum_android'] = $todayRes->scan_sum_android - $yesterdayRes->scan_sum_android;
                    $day_array['scan_sum_ios'] = $todayRes->scan_sum_ios - $yesterdayRes->scan_sum_ios;

                    $day_array['avg_scan_sum'] = $day_array['days']!=0?round($day_array['scan_sum']/$day_array['days'],0):0;

                    $day_array['scan_num'] = $todayRes->scan_num - $yesterdayRes->scan_num;
                    $day_array['red_packet_android'] = $todayRes->red_packet_android - $yesterdayRes->red_packet_android;
                    $day_array['red_packet_ios'] = $todayRes->red_packet_ios - $yesterdayRes->red_packet_ios;

                    $percent = $day_array['scan_sum']!=0?round($day_array['scan_num']/$day_array['scan_sum']*100 ,2):0.00;
                    $day_array['red_packet_percent'] = $percent.'%';

                    $day_array['real_num'] = $todayRes->real_num - $yesterdayRes->real_num;
                    $day_array['avg_real_num'] = $day_array['days']!=0?round($day_array['real_num']/$day_array['days'],0):0;

                    $turn_user=$day_array['scan_num']!=0?round($day_array['real_num']/$day_array['scan_num']*100,2):0.00;
                    $day_array['percent']=$turn_user.'%';

                    $day_array['scene_one'] = $todayRes->scene_one - $yesterdayRes->scene_one;
                    $day_array['scene_two'] = $todayRes->scene_two - $yesterdayRes->scene_two;
                    $day_array['scene_three'] = $todayRes->scene_three - $yesterdayRes->scene_three;
                    $day_array['scene_four'] = $todayRes->scene_four - $yesterdayRes->scene_four;

                    $day_array['scene_one_scan'] = $todayRes->scene_one_scan - $yesterdayRes->scene_one_scan;
                    $day_array['scene_two_scan'] = $todayRes->scene_two_scan - $yesterdayRes->scene_two_scan;
                    $day_array['scene_three_scan'] = $todayRes->scene_three_scan - $yesterdayRes->scene_three_scan;
                    $day_array['scene_four_scan'] = $todayRes->scene_four_scan - $yesterdayRes->scene_four_scan;

                    $day_array['insert_date'] = $todayRes->insert_date;
                    $shop_array[] = (object)$day_array;
                } else if($todayResVal && !$yesterdayResVal){
                    $todayRes = $todayResVal[0];
                    $day_array['sh_shop_id'] = $item->id;
                    $day_array['head_name'] = $item->head_name;
                    $day_array['branch_name'] = $item->branch_name;
                    $day_array['days'] = $todayRes->days;

                    $day_array['scan_sum'] = $todayRes->scan_sum;
                    $day_array['scan_sum_android'] = $todayRes->scan_sum_android;
                    $day_array['scan_sum_ios'] = $todayRes->scan_sum_ios;

                    $day_array['avg_scan_sum'] = $day_array['days']!=0?round($day_array['scan_sum']/$day_array['days'],0):0;

                    $day_array['scan_num'] = $todayRes->scan_num;
                    $day_array['red_packet_android'] = $todayRes->red_packet_android;
                    $day_array['red_packet_ios'] = $todayRes->red_packet_ios;

                    $percent = $day_array['scan_sum']!=0?round($day_array['scan_num']/$day_array['scan_sum']*100 ,2):0.00;
                    $day_array['red_packet_percent'] = $percent.'%';

                    $day_array['real_num'] = $todayRes->real_num;
                    $day_array['avg_real_num'] = $day_array['days']!=0?round($day_array['real_num']/$day_array['days'],0):0;

                    $turn_user=$day_array['scan_num']!=0?round($day_array['real_num']/$day_array['scan_num']*100,2):0.00;
                    $day_array['percent']=$turn_user.'%';

                    $day_array['scene_one'] = $todayRes->scene_one;
                    $day_array['scene_two'] = $todayRes->scene_two;
                    $day_array['scene_three'] = $todayRes->scene_three;
                    $day_array['scene_four'] = $todayRes->scene_four;

                    $day_array['scene_one_scan'] = $todayRes->scene_one_scan;
                    $day_array['scene_two_scan'] = $todayRes->scene_two_scan;
                    $day_array['scene_three_scan'] = $todayRes->scene_three_scan;
                    $day_array['scene_four_scan'] = $todayRes->scene_four_scan;

                    $day_array['insert_date'] = $todayRes->insert_date;
                    $shop_array[] = (object)$day_array;
                }
            }
            return $shop_array;
        }
    }

}