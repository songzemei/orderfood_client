<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="${pageContext.request.contextPath}/img/user2-160x160.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p><security:authentication property="principal.username"/></p>
                <a href="#"><i class="fa fa-circle text-success"></i> 在线</a>
            </div>
        </div>
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">菜单</li>
            <li id="admin-index"><a href="${pageContext.request.contextPath}/main.jsp"><i
                    class="fa fa-dashboard"></i><span>首页</span></a>
            </li>
            <%-- 系统管理 --%>
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-cogs"></i><span>我的账户</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu">
                    <li id="user-setting">
                        <a href="${pageContext.request.contextPath}/address/all">
                            <i class="fa fa-circle-o"></i>我的收货地址
                        </a>
                    </li>

                    <li id="rigister-rider-setting">
                        <a href="${pageContext.request.contextPath}/rigister_rider.jsp">
                            <i class="fa fa-circle-o"></i>注册成为骑手
                        </a>
                    </li>

                    <li id="rigister-rider-setting">
                        <a href="${pageContext.request.contextPath}/member/balance">
                            <i class="fa fa-circle-o"></i>充钱
                        </a>
                    </li>

                    <security:authorize access="hasAnyRole({'ROLE_RIDER'})">
                    <li id="cancel-rider-setting">
                        <a href="${pageContext.request.contextPath}/cancel_rider.jsp">
                            <i class="fa fa-circle-o"></i>注销骑手身份
                        </a>
                    </li>
                    </security:authorize>

                </ul>
            </li>

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-cube"></i><span>去点外卖</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu">
                    <li id="product-manage">
                        <a href="${pageContext.request.contextPath}/product/all">
                            <i class="fa fa-circle-o"></i>查看菜单
                        </a>
                    </li>
                    <li id="car-manage">
                        <a href="${pageContext.request.contextPath}/car/all">
                            <i class="fa fa-circle-o"></i>我的购物车
                        </a>
                    </li>
                    <li id="orders-manage">
                        <a href="${pageContext.request.contextPath}/orders/all">
                            <i class="fa fa-circle-o"></i>我的订单
                        </a>
                    </li>
                </ul>
            </li>

            <security:authorize access="hasAnyRole({'ROLE_RIDER'})">
                <li class="treeview">
                    <a href="#">
                        <i class="fa fa-cube"></i><span>去接单</span>
                        <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                    </a>
                    <ul class="treeview-menu">
                        <li>
                            <a href="${pageContext.request.contextPath}/orders/allUnFinish">
                                <i class="fa fa-circle-o"></i>发现待配送订单
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/orders/allByRiderId">
                                <i class="fa fa-circle-o"></i>我已配送的订单
                            </a>
                        </li>
                    </ul>
                </li>
            </security:authorize>

        </ul>
    </section>
    <!-- /.sidebar -->
</aside>