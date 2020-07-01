#主从模式
    1.create -e  /master "master1.com"
    2.stat -w /master
#任务分配
     1. 创建永久节点 /workers /task /assign
     2. 主机注解点监控 /workes /task
     3. 从节点注册临时节点worker，永久节点/assign
