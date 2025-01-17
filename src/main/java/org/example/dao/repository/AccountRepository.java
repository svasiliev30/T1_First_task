package org.example.dao.repository;

import org.example.dao.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository <Account,Long>{
}
