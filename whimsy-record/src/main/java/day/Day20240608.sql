-- 001号用户 202406 1号 sign完成
SETBIT demo:sign:001:202406 0 1

-- 001号用户 202406 2号 sign完成
SETBIT demo:sign:001:202406 1 1

-- 001号用户 202406 3号 sign完成
SETBIT demo:sign:001:202406 2 1

-- 001号用户 202406 4号 sign完成
SETBIT demo:sign:001:202406 3 1

-- 001号用户 202406 1号 sign 是否完成
getbit demo:sign:001:202406 1

-- 001号用户 202406 sign次数
bitcount demo:sign:001:202406

-- 首次打卡
BITPOS demo:sign:001:202406 1


SETBIT demo:sign:001:202407 0 1
bitcount demo:sign:001:202407


-- 001 用户 67月份工打卡次数
BITOP or destmap demo:sign:001:202406 demo:sign:001:202407
BITCOUNT destmap

del demo:sign:001:202406
del demo:sign:001:202407
del destmap
