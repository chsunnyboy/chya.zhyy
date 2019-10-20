package chya.zhyy.user;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chya.zhyy.BaseService;

@Service(SysUserService.SERVICE_NAME)
@Transactional
public class SysUserServiceImpl extends BaseService<SysUserQuery> implements SysUserService {

	@Override
	public Map<String, Object> search() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSelect(SysUserQuery t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFrom(SysUserQuery t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWhere(SysUserQuery t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
