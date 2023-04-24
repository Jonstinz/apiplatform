import { Dropdown } from 'antd';
import type { DropDownProps } from 'antd/es/dropdown';
import React from 'react';
import { useEmotionCss } from '@ant-design/use-emotion-css';
import classNames from 'classnames';
import styles from './index.less';

export type HeaderDropdownProps = {
  overlayClassName?: string;
  // overlay: React.ReactNode | (() => React.ReactNode) | any;
  placement?: 'bottomLeft' | 'bottomRight' | 'topLeft' | 'topCenter' | 'topRight' | 'bottomCenter';
} & Omit<DropDownProps, 'overlay'>;

const HeaderDropdown: React.FC<HeaderDropdownProps> = ({ overlayClassName: cls, ...restProps }) => {
  // const className = useEmotionCss(({ token }) => {
  //   return {
  //     [`@media screen and (max-width: ${token.screenXS})`]: {
  //       width: '100%',
  //     },
  //   };
  // });
  return <Dropdown overlayClassName={classNames(styles.container, cls)} {...restProps} />;
};

export default HeaderDropdown;
