export default [
  {
    path: '/',
    name: '主页',
    icon: 'smile',
    component: './Index'
  },
  {
    path: '/interface_info/:id',
    name: '查看接口',
    icon: 'smile',
    component: './InterfaceInfo',
    hideInMenu: true
  },
  // {
  //   path: '/account',
  //   name: '个人信息',
  //   icon: 'user',
  //   routes: [
  //     {name: '个人中心', path: '/account/center', component: './Account/center'},
  //     {name: '个人设置', path: '/account/settings', component: './Account/settings'}
  //   ],
  // },
  {
    name: '个人中心',
    icon: 'user',
    path: '/account',
    routes: [
      {
        path: '/account',
        redirect: '/Account/center',
      },
      {
        name: '个人信息',
        icon: 'smile',
        path: '/account/settings',
        component: './Account/settings',
      },
    ],
  },
  {
    path: '/user',
    layout: false,
    routes: [
      {name: '登录', path: '/user/login', component: './User/Login'},
      {name: '注册', path: '/user/register', component: './User/Register'}
    ],
  },
  {
    path: '/admin',
    name: '管理页',
    icon: 'crown',
    access: 'canAdmin',
    routes: [
      {name: '接口管理页', icon: 'table', path: '/admin/interface_info', component: './Admin/InterfaceInfo'},
      {name: '接口分析页', icon: 'table', path: '/admin/interface_analysis', component: './Admin/InterfaceInfoAnalysis'},
    ],
  },
  {path: '*', layout: false, component: './404'},
];
