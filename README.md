# iSpaceSync
项目简介
==
该项目的主要目的是在不使用磁力计的情况下，利用一个一致的合力对多个传感器设备进行坐标系的同步。这个项目用于导出一个jar包，供其它应用使用。
使用方式
==
demo可以参考[PC段](https://github.com/LeoCai/SpaceSync-PC-Demo)和[Androi端](https://github.com/LeoCai/SpaceSync-Android-Demo)的两个Demo项目
```java
DataServerMultiClient dataServerMultiClient = new DataServerMultiClient();//构造服务端实例

//等待传感器设备连接完后构造算法与显示
int clientsNum = dataServerMultiClient.getClientsNum();
SpaceSyncPCFrameDataListener frameDataListener = new SpaceSyncPCFrameDataListener("SPACE SYNC PLOT",　clientsNum);
TrackingCallBack[] trackingCallBacks = new TrackingCallBack[clientsNum];
for (int i = 0; i < clientsNum; i++) {
  PhoneDisplayerPCImpl pcImpl = new PhoneDisplayerPCImpl();
  frameDataListener.addPhoneView(pcImpl.getWorldView().getUniverse().getCanvas());
  trackingCallBacks[i] = new PhoneViewCallBack(pcImpl);
}

//此处获得一个默认的算法模块
SpaceSync spaceSync = SpaceSyncFactory.getDefaultSpaceSync(clientsNum, trackingCallBacks, frameDataListener, frameDataListener);
//注册数据监听
Observer spaceSyncOb = new ObserverSpaceSyncMultiClient(clientsNum, spaceSync);
dataServerMultiClient.addDataListener(spaceSyncOb);

//开始监听数据
log("Ready to receive data!");
try {
  dataServerMultiClient.receivedData();
} catch (IOException e1) {
  e1.printStackTrace();
}
```
