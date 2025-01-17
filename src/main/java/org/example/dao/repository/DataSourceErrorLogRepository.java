package org.example.dao.repository;

import org.aspectj.lang.annotation.Pointcut;
import org.example.dao.entity.Account;
import org.example.dao.entity.DataSourceErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSourceErrorLogRepository extends JpaRepository<DataSourceErrorLog,Long> {

}
