/**   
* @Title: UserDaoImpl.java 
* @Package org.dao.impl 
* @Description: TODO该方法的主要作用： 
* @author A18ccms A18ccms_gmail_com   
* @date 2017-9-22 下午8:52:58 
* @version V1.0   
*/
package org.dao.impl;

import org.dao.IUserDao;
import org.entity.Users;

/**
 * 
 * 项目名称：test_face_photo 类名称：UserDaoImpl 类描述： 创建人：Mu Xiongxiong 创建时间：2017-9-22
 * 下午8:52:58 修改人：Mu Xiongxiong 修改时间：2017-9-22 下午8:52:58 修改备注：
 * 
 * @version
 * 
 */
public class UserDaoImpl extends BaseDaoUtilImpl<Users> implements IUserDao {
	/**
	 * (非 Javadoc)
	 * <p>
	 * Description(描述): 注册
	 * </p>
	 * <p>
	 * Title: save
	 * </p>
	 * 
	 * @param entity
	 * @return
	 * @see org.dao.impl.BaseDaoUtilImpl#save(java.lang.Object)
	 */
	@Override
	public int save(Users entity) {
		return super.save(entity);
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Description(描述):登陆
	 * </p>
	 * <p>
	 * Title: queryInfoByUsername
	 * </p>
	 * 
	 * @param username
	 * @return
	 * @see org.dao.impl.BaseDaoUtilImpl#queryInfoByUsername(java.lang.String)
	 */
	@Override
	public Users queryInfoByUsername(String username) {
		// TODO Auto-generated method stub
		return super.queryInfoByUsername(username);
	}

}