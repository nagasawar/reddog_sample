package reddog_sample.service;

import org.seasar.framework.container.SingletonS2Container;

/**
 * サービスの呼び出しを一括管理するクラス。
 * SFは、ServiceFactoryの略
 */
public class SF {

    public static CommonService common = SingletonS2Container.getComponent(CommonService.class);
    public static DepartmentService department = SingletonS2Container.getComponent(DepartmentService.class);
    public static EmployeeService employee = SingletonS2Container.getComponent(EmployeeService.class);
    public static RdPageAuthService rdPageAuth = SingletonS2Container.getComponent(RdPageAuthService.class);
    public static RdUserService rdUser = SingletonS2Container.getComponent(RdUserService.class);
    public static RdUserRoleService rdUserRole = SingletonS2Container.getComponent(RdUserRoleService.class);
}
