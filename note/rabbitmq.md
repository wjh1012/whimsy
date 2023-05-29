## 交换机类型
> 扇形: Fanout Exchange
> 
投递到所有绑定的队列，不需要路由键，不需要进行路由键的匹配，相当于广播、群发  
![](https://wjh-tubed.oss-cn-chengdu.aliyuncs.com/20235/1684509984877.png "广播")
> 直连: Direct Exchange

根据路由键精确匹配（一模一样）进行路由消息队列  
![](https://wjh-tubed.oss-cn-chengdu.aliyuncs.com/20235/1684510130568.png)

> 主题: Topic Exchange

通配符匹配，相当于模糊匹配：  
匹配多个单词，用来表示任意数量（单个或多个）单词  
匹配一个单词（必须有一个，而且只有一个），用隔开的为一个单词：  
beijing.# == beijing.queue.abc, beijing.queue.xyz.xxx  
beijing.* == beijing.queue, beijing.xyz  
![](https://wjh-tubed.oss-cn-chengdu.aliyuncs.com/20235/1684510249945.png)

> 头部: Headers Exchange

基于消息内容中的neaders属性进行匹配  
![](https://wjh-tubed.oss-cn-chengdu.aliyuncs.com/20235/1684510275274.png)


