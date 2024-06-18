-- geoadd指令携带集合名称以及多个经纬度名称三元组，注意可以是多个三元组，如下指令，我们一共添加了五个公司的经纬度。

geoadd company 116.48105 39.996794 juejin
geoadd company 116.514203 39.905409 ireader
geoadd company 116.489033 40.007669 meituan
geoadd company 116.562108 39.787602 jd 116.334255 40.027400 xiaomi



-- geodist指令可以用来计算两个元素之间的距离，携带集合名称、两个单位名称和距离单位(距离单位可以是 m、km、ml 和 ft)，分别代表米、千米、英里和尺。

-- 10.5501 km
geodist company juejin ireader km
-- 24273.939 m
geodist company juejin jd m


-- geopos指令可以获取集合中任意元素的经纬度坐标，可以一次获取多个。
-- geoadd进去的经纬度和geopos出来的坐标有少许误差，原因是GeoHash对二维坐标进行的一维映射是有损的，

geopos company juejin
geopos company jd meituan

-- GeoHash可以获取元素的经纬度编码字符串，可以通过这个编码去 geohash.org/${hash} 上直接进行定位。

geohash company meituan


-- georadiusbymember用于查询指定元素附近的其他元素。
georadiusbymember company juejin 20 km count 3 asc


-- georadius 可以根据经纬度获取周围的其它元素，例如附近的车、餐馆、人。
georadius company 116.514202 39.905409 20 km withdist count 3 desc


del company
