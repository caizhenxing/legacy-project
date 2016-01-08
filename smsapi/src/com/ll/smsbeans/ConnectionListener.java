

package com.ll.smsbeans;

/**
 *  <code>ConnectionListener</code>为一个监听接口,你可以通过实现接口和注册对连接状态改变事件的处理 。
 * <p>
 * 
 * 
 * <code>ConnectionAdapter</code> 为本接口实现的类，它仅仅列出一些不作任何事情的方法，使你可以通过继承这个类的方式，来处理状态的改变。
 * <p>
 * 这些所有的事件都是由ConnectionBean对象产生的，并且它还实现了对事件监听器的注册。
 * <p>
 * @see ConnectionAdapter
 * @see ConnectionBean
 *
 * @author  listlike <a href="mailto:listlike@hotmail.com">
 *                      <i>&lt;listlike@hotmail.com&gt;</i></a>
 *
 */
public interface ConnectionListener
{
    /**
     * <code>connectionChanged</code> 接收到状态改变的事件。
     */
    void connectionChanged(ConnectionEvent ce);
}
