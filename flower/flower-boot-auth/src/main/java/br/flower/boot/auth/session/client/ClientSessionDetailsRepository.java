package br.flower.boot.auth.session.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientSessionDetailsRepository extends JpaRepository<ClientSessionDetails, String>{

}
