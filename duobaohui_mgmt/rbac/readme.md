1. 测试环境，更改config/local 为dev, 更改config/dev/Mysql 配置,
2. 正式环境，更改config/local 为production, 更改config/production/Mysql配置, 第一次上线以后不再更新config/local
3. 配置hosts 127.0.0.1 localhost.rbac, 配置nginx, localhost.rbac 指向rbac/apple/public
