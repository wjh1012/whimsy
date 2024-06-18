PFADD 数据库使用:Redis数据 user1 user2 user3 user1
PFADD 数据库使用:MySQL数据 user1 user2 user4

-- 3
PFCOUNT 数据库使用:Redis数据
-- 3
PFCOUNT 数据库使用:MySQL数据

PFMERGE 数据库 数据库使用:Redis数据 数据库使用:MySQL数据
-- 4
PFCOUNT 数据库

del 数据库使用:Redis数据
del 数据库使用:MySQL数据
del 数据库

