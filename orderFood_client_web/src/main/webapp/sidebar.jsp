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
                    <i class="fa fa-cogs"></i><span>账户管理</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu">
                    <li id="user-setting">
                        <a href="${pageContext.request.contextPath}/address/all">
                            <i class="fa fa-circle-o"></i>地址管理
                        </a>
                    </li>
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
                        <a href="${pageContext.request.contextPath}/product/findAll">
                            <i class="fa fa-circle-o"></i>产品管理
                        </a>
                    </li>
                    <li id="order-cancel">
                        <a href="${pageContext.request.contextPath}/orders/findAll">
                            <i class="fa fa-circle-o"></i>订单管理
                        </a>
                    </li>
                    <li id="member-cancel">
                        <a href="${pageContext.request.contextPath}/member/findAll">
                            <i class="fa fa-circle-o"></i>会员管理
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>