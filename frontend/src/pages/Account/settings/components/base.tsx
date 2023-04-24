import React, {useState} from 'react';
import {PlusOutlined, UploadOutlined} from '@ant-design/icons';
import {Button, Input, Upload, message, Switch} from 'antd';
import ProForm, {
  ProFormDependency,
  ProFormFieldSet, ProFormRadio,
  ProFormSelect,
  ProFormText,
  ProFormSwitch,
  ProFormTextArea,
} from '@ant-design/pro-form';
import { useRequest } from 'umi';
import { queryCurrent } from '../service';
import { queryProvince, queryCity } from '../service';

import styles from './BaseView.less';
import {updateUserUsingPOST} from "@/services/Apiplatform_backend/userController";
import {options} from "kolorist";
import {data} from "@umijs/utils/compiled/cheerio/lib/api/attributes";
import initialState from "@@/plugin-initialState/@@initialState";
import {getInitialState} from "@/app";


const validatorPhone = (rule: any, value: string[], callback: (message?: string) => void) => {
  if (!value[0]) {
    callback('Please input your area code!');
  }
  if (!value[1]) {
    callback('Please input your phone number!');
  }
  callback();
};
// 头像组件 方便以后独立，增加裁剪之类的功能
const AvatarView = ({ avatar }: { avatar: string }) => (
  <>
    <div className={styles.avatar_title}>头像</div>
    <div className={styles.avatar}>
      <img src={avatar} alt="avatar" />
    </div>
    <Upload showUploadList={false}>
      <div className={styles.button_view}>
        <Button>
          <UploadOutlined />
          更换头像
        </Button>
      </div>
    </Upload>
  </>
);

const BaseView: React.FC = () => {
  const { data: currentUser, loading } = useRequest(() => {
    return queryCurrent();
  });

  const getAvatarURL = () => {
    if (currentUser) {
      if (currentUser.userAvatar) {
        return currentUser.userAvatar;
      }
      const url = 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png';
      return url;
    }
    return '';
  };

  const [readonly, setReadonly] = useState(false);
  const handleFinish = async (values: any) => {
    // @ts-ignore
    const res = await updateUserUsingPOST({
      id: currentUser!.id,
      ...values
    })
    message.success('更新基本信息成功');
  };
  return (

    <div className={styles.baseView}>
      {loading ? null : (
        <>
          <div className={styles.left}>
            <Switch
              style={{
                marginBlockEnd: 16,
              }}
              checked={readonly}
              checkedChildren="编辑"
              unCheckedChildren="只读"
              onChange={setReadonly}
            />
            <ProForm
              // initialValues={getInitialState()}
              readonly={!readonly}
              layout="vertical"
              onFinish={handleFinish}
              submitter={{

                searchConfig: {

                  submitText: '更新基本信息',
                },
                render: (_, dom) => dom[1],
              }}
              initialValues={currentUser}
              hideRequiredMark
            >

              <ProFormText
                width="md"
                name="userName"
                label="用户名"
                rules={[
                  {
                    required: true,
                    message: '请输入您的用户名!',
                  },
                ]}
              />
              <ProFormText
                readonly={true}
                width="md"
                name="userAccount"
                label="账号"
                rules={[
                  {
                    required: true,
                    message: '请输入您的账号!',
                  },
                ]}
              />
              <ProFormRadio.Group
                width="md"
                name="gender"
                label="性别"
                options={[
                  {
                    label:'男',
                    value:'1',
                  },
                  {
                    label:'女',
                    value:'0',
                  },
                ]}
                rules={[
                  {
                    required: true,
                  },

                ]}
              />

            </ProForm>

          </div>
          <div className={styles.right}>
            <AvatarView avatar={getAvatarURL()} />
          </div>
        </>
      )}
    </div>
  );
};

export default BaseView;
