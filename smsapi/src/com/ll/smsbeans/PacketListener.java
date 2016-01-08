
package com.ll.smsbeans;

/**
 * <code>PacketListener</code> 客户端响应包事件的接口。
 *
 * @author  listlike <a href="mailto:listlike@hotmail.com">
 *                      <i>&lt;listlike@hotmail.com&gt;</i></a>
 *
 */
public interface PacketListener
{
    /**
     * 当有包到达的时候调用的方法。 
     *
     * @param pe PacketEvent 刚刚到达的包事件。
     */
    void receivedPacket(PacketEvent pe);

    /**
     *当客户端发送包成功后调用的方法。
     *
     * @param pe PacketEvent 发送成功后的事件。
     */
    void sentPacket(PacketEvent pe);

    /**
     * 当包没有发送成功的时候调用 (例如：如果连接断开的时候，包在发送队列中，或者发送的时候断开连接)。
     *
     * @param pe PacketEvent 发送失败的事件。
     */
    void sendFailed(PacketEvent pe);
}
