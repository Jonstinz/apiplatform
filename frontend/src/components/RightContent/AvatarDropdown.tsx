import { outLogin } from '@/services/ant-design-pro/api';
import { LogoutOutlined, SettingOutlined, UserOutlined } from '@ant-design/icons';
import { useEmotionCss } from '@ant-design/use-emotion-css';
import { history, useModel } from '@umijs/max';
import {Avatar, Menu, Spin} from 'antd';
import { stringify } from 'querystring';
import type { MenuInfo } from 'rc-menu/lib/interface';
import React, { useCallback } from 'react';
import { flushSync } from 'react-dom';
import styles from './index.less';
import HeaderDropdown from '../HeaderDropdown';
import {userLogoutUsingPOST} from "@/services/Apiplatform_backend/userController";

export type GlobalHeaderRightProps = {
  menu?: boolean;
  children?: React.ReactNode;
};

export const AvatarName = () => {
  const { initialState } = useModel('@@initialState');
  const  currentUser  = initialState || {};
  return <span className="anticon">{currentUser.loginUser?.userName}</span>;
  // return <span className="anticon">{currentUser?.userName}</span>;
};

export const AvatarDropdown: React.FC<GlobalHeaderRightProps> = ({ menu, children }) => {
  /**
   * 退出登录，并且将当前的 url 保存
   */
  const userLogout = async () => {
    await userLogoutUsingPOST();
    const { search, pathname } = window.location;
    const urlParams = new URL(window.location.href).searchParams;
    /** 此方法会跳转到 redirect 参数所在的位置 */
    const redirect = urlParams.get('redirect');
    // Note: There may be security issues, please note
    if (window.location.pathname !== '/user/login' && !redirect) {
      history.replace({
        pathname: '/user/login',
        search: stringify({
          redirect: pathname + search,
        }),
      });
    }
  };
  // const actionClassName = useEmotionCss(({ token }) => {
  //   return {
  //     display: 'flex',
  //     height: '48px',
  //     marginLeft: 'auto',
  //     overflow: 'hidden',
  //     alignItems: 'center',
  //     padding: '0 8px',
  //     cursor: 'pointer',
  //     borderRadius: token.borderRadius,
  //     '&:hover': {
  //       backgroundColor: token.colorBgTextHover,
  //     },
  //   };
  // });
  const { initialState, setInitialState } = useModel('@@initialState');

  const onMenuClick = useCallback(
    (event: MenuInfo) => {
      const { key } = event;
      if (key === 'logout') {
        flushSync(() => {
          setInitialState((s) => ({ ...s, currentUser: undefined }));
        });
        userLogout();
        return;
      }
      history.push(`/account/${key}`);
    },
    [setInitialState],
  );

  const loading = (
    <span className={`${styles.action} ${styles.account}`}>
      <Spin
        size="small"
        style={{
          marginLeft: 8,
          marginRight: 8,
        }}
      />
    </span>
  );

  if (!initialState) {
    return loading;
  }

  const  currentUser  = initialState.loginUser;

  if (!currentUser || !currentUser.userName) {
    return loading;
  }
  if (currentUser){
    menu = true;
  }
  const menuItems = [
    ...(menu
      ? [
          {
            key: 'settings',
            icon: <UserOutlined />,
            label: '个人信息',
          },
          {
            type: 'divider' as const,
          },
        ]
      : []),
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '退出登录',
    },
  ];
  // const menuItems = (
  //   <Menu className={styles.menu} selectedKeys={[]} onClick={onMenuClick}>
  //     {menu && (
  //       <Menu.Item key="settings">
  //         <UserOutlined />
  //         个人信息
  //       </Menu.Item>
  //     )}
  //
  //     {menu && <Menu.Divider />}
  //
  //     <Menu.Item key="logout">
  //       <LogoutOutlined />
  //       退出登录
  //     </Menu.Item>
  //   </Menu>
  // );


  return (
    <HeaderDropdown

      menu={{
        selectedKeys: [],
        onClick: onMenuClick,
        items: menuItems,
      }}
    >
      {children}
    </HeaderDropdown>
    // <HeaderDropdown overlay={menuItems}>
    //   <span className={`${styles.action} ${styles.account}`}>
    //     <Avatar size="small" className={styles.avatar} src={"https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png"} alt="avatar" />
    //     <span className={`${styles.name} anticon`}>{!currentUser.userName}</span>
    //   </span>
    // </HeaderDropdown>
  );
};
