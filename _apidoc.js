// ------------------------------------------------------------------------------------------
// General apiDoc documentation blocks and old history blocks.
// ------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------
// Current Permissions.
// ------------------------------------------------------------------------------------------
/**
 * @apiDefine user 需要传入一个token作为权限验证
 * 需要header中传递Authorization
 * @apiVersion 0.0.1
 */

/**
 * @apiDefine admin 需要传入一个token作为权限验证,且具有管理员角色
 * 需要权限为admin的用户
 * @apiVersion 0.0.1
 */

/**
 * @apiDefine supperAdmin 需要传入一个token作为权限验证,且具有超级管理员角色
 * 需要权限为admin的用户
 * @apiVersion 0.0.1
 */

/**
 * @apiDefine analyst 需要传入一个token作为权限验证,且具有超级管理员角色
 * 需要权限为analyst的用户
 * @apiVersion 0.0.1
 */

/**
 * @apiDefine none 无需登录授权
 * 无需登录授权
 */

/**
 * @apiDefine SUCCESS  成功
 * @apiSuccessExample {json} 成功返回:
 * {"code":0,"msg":"成功","data":null}
 */


/**
 * @apiDefine tokenMsg 全局配置token鉴权请求头
 * @apiHeader {String} Authorization 鉴权信息：为Bearer + "空格" +  {token}
 */
// ------------------------------------------------------------------------------------------
// History.
// ------------------------------------------------------------------------------------------
