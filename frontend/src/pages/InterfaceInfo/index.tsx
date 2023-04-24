// @ts-ignore
import {PageContainer} from '@ant-design/pro-components';
import {useModel} from '@umijs/max';
import {Badge, Card, Descriptions, Form, List, message, theme, Input, Button, Space, Progress} from 'antd';
import React, {useEffect, useRef, useState} from 'react';
import {
  getInterfaceInfoByIdUsingGET, invokeInterfaceInfoUsingPOST,
} from "@/services/Apiplatform_backend/interfaceInfoController";
import {useParams} from "react-router";
import {
  addUserInterfaceInfoUsingPOST,
  getUserInterfaceNumByIdUsingGET, updateUserInterfaceInfoUsingPOST,
} from "@/services/Apiplatform_backend/userInterfaceInfoController";
import initialState from "@@/plugin-initialState/@@initialState";
import {getLoginUserUsingGET} from "@/services/Apiplatform_backend/userController";
import {Simulate} from "react-dom/test-utils";
import error = Simulate.error;


/**
 * 接口文档
 * @returns
 */
const Index: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [interfaceInfoData, setinterfaceInfoData] = useState<API.InterfaceInfo>();
  const [userinterfaceNumData, setuserinterfaceNumData] = useState<API.UserInterfaceInfo>();
  const [currentuser,setCurrentuser] = useState<API.User>();
  const params = useParams();
  const [invokeRes, setInvokeRes] = useState<any>();
  const [invokeLoading, setInvokeLoading] = useState(false);
  const [flags,setFlags] = useState(false);
  const userinterfaceNumDataRef =useRef({totalNum:0,leftNum:0});



  const onFinish = async (values: any) => {
    if (!params.id) {
      message.error("接口不存在");
      return;
    }
    setInvokeLoading(true);

    try {
      const res = await invokeInterfaceInfoUsingPOST({
        id: params.id,
        ...values
      })
        message.success("请求成功");
        setInvokeRes(res.data)
        setuserinterfaceNumData({
          totalNum: Number(userinterfaceNumData?.totalNum) + 1,
          leftNum: Number(userinterfaceNumData?.leftNum) - 1
        })

    } catch (error: any) {
      message.error('请求失败' + error.message);
    }
    setInvokeLoading(false);
    return false;
  };
  const loadData = async () => {
    //获取url中id的值
    if (!params.id) {
      message.error('参数不存在');
      return;
    }
    try {
      const currentuser = await getLoginUserUsingGET();
      const userid = Number(currentuser.data!.id);
      const res = await getInterfaceInfoByIdUsingGET({
        id: Number(params.id)
      });
      const res2 = await getUserInterfaceNumByIdUsingGET({
        interfaceInfoId: Number(params.id),
        userId: Number(userid)});
      console.log("res2:",res2.data)
      //将返回值data存入Data中
      // setuserinterfaceNumData(res2.data);
      console.log("init_userinterfaceNumData?.leftNum:"+userinterfaceNumData?.leftNum)
      // setuserinterfaceNumData({totalNum:Number(res2.data.totalNum),leftNum:Number(res2.data.leftNum)});
      if (res2.data == null){
        console.log("come if")
        setuserinterfaceNumData( {
          totalNum : 0,
          leftNum :0
        })
          // setFlags(true);
      }else {console.log("come else")
        setuserinterfaceNumData(res2.data)
        if(Number(res2.data.leftNum) > 5){
          console.log("come in")
          setFlags(true);
        }}
      console.log("userinterfaceNumData:",userinterfaceNumData)
      // const leftNum =  Number(userinterfaceNumData?.leftNum);
      // console.log(typeof userinterfaceNumData?.leftNum)
      // console.log("leftNum:"+leftNum)

      setinterfaceInfoData(res.data);
    } catch (error: any) {
      message.error('请求失败' + error.message);
    }

    setLoading(false);
  }
  const getNum = async (values: any) => {
    const currentuser = await getLoginUserUsingGET();
    const userid = Number(currentuser.data!.id);
    const res2 = await getUserInterfaceNumByIdUsingGET({
      interfaceInfoId: Number(params.id),
      userId: Number(userid)});
    if (res2.data == null){
      try {
        await addUserInterfaceInfoUsingPOST({
          userId: Number(userid),
          interfaceInfoId:Number(params.id),
          totalNum:0,
          leftNum:5,
        })
        message.success("请求成功");
        setuserinterfaceNumData( {
          leftNum :5,
          totalNum:userinterfaceNumData?.totalNum
        })
      } catch (error: any) {
        message.error('请求失败' + error.message);
      }
    }
    else {
      try {
        const userInterId = Number(res2.data.id);
        await updateUserInterfaceInfoUsingPOST({
          id:userInterId,
          userId: userid,
          interfaceInfoId:params.id,
          leftNum:Number(res2.data.leftNum )+ 5,
        })
        message.success("请求成功");
        setuserinterfaceNumData( {
          leftNum :Number(res2.data.leftNum )+ 5,
          totalNum:userinterfaceNumData?.totalNum
        })
        setFlags(true);
      }catch (error: any) {
        message.error('请求失败' + error.message);
      }
    }

  };
  useEffect(() => {
    loadData();
    // userinterfaceNumDataRef.current = userinterfaceNumData;
  }, [])
  // function verifyNum() {
  //   const currentuser =  getLoginUserUsingGET();
  //   const userid = Number(currentuser.data.id);
  //   const res =  getInterfaceInfoByIdUsingGET({
  //     id: Number(params.id)
  //   });
  //   const res2 = await getUserInterfaceNumByIdUsingGET({
  //     interfaceInfoId: Number(params.id),
  //     userId: Number(userid)
  //
  //   });
  //   const leftNum = Number(res2.data.leftNum);
  //   if(leftNum > 5){
  //     setFlags(true);
  //   }
  // }
  return (
    <PageContainer title="查看接口文档">
      <Card>
        {interfaceInfoData ? (
          <Descriptions title={interfaceInfoData.name} column={1}>
            {/*//todo 封装后端参数转递剩余调用次数*/}
            <Descriptions.Item label="已经调用次数">{userinterfaceNumData?.totalNum? userinterfaceNumData.totalNum : 0}</Descriptions.Item>
            <Descriptions.Item label="剩余调用次数">
              <Space wrap>
                <Progress type="circle" percent={userinterfaceNumData?.leftNum} format={(percent) => `${percent} 次`} />
              </Space>
            </Descriptions.Item>
            <Descriptions.Item label="接口状态" span={3}>
              <Badge status="processing" text={interfaceInfoData.status ? '开启' : '关闭'}/>
            </Descriptions.Item>
            <Descriptions.Item label="描述">{interfaceInfoData.description}</Descriptions.Item>
            <Descriptions.Item label="请求方法">{interfaceInfoData.method}</Descriptions.Item>
            <Descriptions.Item label="请求参数">{interfaceInfoData.requestParams}</Descriptions.Item>
            <Descriptions.Item label="请求头">{interfaceInfoData.requestHeader}</Descriptions.Item>
            <Descriptions.Item label="响应头">{interfaceInfoData.responseHeader}</Descriptions.Item>
            <Descriptions.Item label="创建时间">{interfaceInfoData.createTime}</Descriptions.Item>
            <Descriptions.Item label="更新时间">{interfaceInfoData.updateTime}</Descriptions.Item>
            <Descriptions.Item label= "领取免费次数">
              <Button key="get" type="primary" onClick={getNum} disabled={flags}>
                领取
              </Button>
            </Descriptions.Item>
          </Descriptions>
        ) : (
          <>接口不存在</>
        )}
      </Card>
      <Card>
        <Form
          name="invoke"
          layout="vertical"
          onFinish={onFinish}
        >
          <Form.Item
            label="请求参数"
            name="userRequestParams"
          >
            <Input.TextArea/>
          </Form.Item>
          <Form.Item wrapperCol={{span: 16}}>
            <Button type="primary" htmlType="submit">
              调用
            </Button>
          </Form.Item>
        </Form>
      </Card>
      <Card>
        <Input.TextArea
          autoSize
          value={invokeRes}>
        </Input.TextArea>
      </Card>
    </PageContainer>
  );
}


export default Index;
