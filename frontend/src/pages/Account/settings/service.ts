import { request } from 'umi';
import type { CurrentUser, GeographicItemType } from './data';
import {getLoginUserUsingGET, getUserByIdUsingGET} from "@/services/Apiplatform_backend/userController";

export async function queryCurrent(): Promise<{ data: CurrentUser }> {
  const res = await getLoginUserUsingGET();
  return res;
}

export async function queryProvince(): Promise<{ data: GeographicItemType[] }> {
  return request('/api/geographic/province');
}

export async function queryCity(province: string): Promise<{ data: GeographicItemType[] }> {
  return request(`/api/geographic/city/${province}`);
}

export async function query() {
  return request('/api/users');
}
