package com.proxy.ip.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proxy.ip.po.IpProxyInfo;

@Repository
public interface IpProxyInfoDao extends JpaRepository<IpProxyInfo, Serializable>, JpaSpecificationExecutor<IpProxyInfo> {
	
	@Query(value = "  select *  from ip_proxy_info a where delete_flag = 0 and  create_time > :longTime  limit :size", nativeQuery = true)
	List<IpProxyInfo> findList(@Param("size") Integer size, @Param("longTime") String longTime);

	@Query(value = "  select *  from ip_proxy_info a where ip=:ip and port=:port", nativeQuery = true)
	IpProxyInfo findOneByIpAndPort(@Param("ip") String ip, @Param("port") Integer port);

	@Modifying
	@Query(value = "  update  ip_proxy_info set delete_flag = :deleteFlag  where id=:id ", nativeQuery = true)
	void update(@Param("id") Integer id, @Param("deleteFlag") Integer deleteFlag);

	@Modifying
	@Query(value = "  delete from  ip_proxy_info  where deleteFlag =:deleteFlag ", nativeQuery = true)
	void deleteAllByDeleteFlag(@Param("deleteFlag") Integer deleteFlag);

	@Modifying
	@Query(value = "  delete from  ip_proxy_info  where id =:id ", nativeQuery = true)
	void deleteOne(@Param("id") Integer id);

	@Query(value = "  select *  from ip_proxy_info a where delete_flag = 0 and  create_time > :longTime order by rand() limit 1", nativeQuery = true)
	IpProxyInfo findOne(@Param("longTime") String longTime);
}
