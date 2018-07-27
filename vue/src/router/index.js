import Vue from 'vue'
import Router from 'vue-router'
import test from '../components/test'
import pageAdminExceptionInfo from '../components/page-admin-exception-info'
import pageAdminFileInfoOwn from '../components/page-admin-file-info-own'
import pageAdminPermission from '../components/page-admin-permission'
import pageAdminUserAuthorization from '../components/page-admin-user-authorization'
import pageAdminUser from '../components/page-admin-user'
import pageUploadFile from '../components/page-upload-file'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'test',
      component: test
    },
    {
      path: '/admin/exceptionInfo',
      name: 'pageAdminExceptionInfo',
      component: pageAdminExceptionInfo
    },
    {
      path: '/admin/fileInfoOwn',
      name: 'pageAdminFileInfoOwn',
      component: pageAdminFileInfoOwn
    },
    {
      path: '/admin/permission',
      name: 'pageAdminPermission',
      component: pageAdminPermission
    },
    {
      path: '/admin/userAuthorization',
      name: 'pageAdminUserAuthorization',
      component: pageAdminUserAuthorization
    },
    {
      path: '/admin/user',
      name: 'pageAdminUser',
      component: pageAdminUser
    },
    {
      path: '/uploadFile',
      name: 'pageUploadFile',
      component: pageUploadFile
    },
  ]
})
