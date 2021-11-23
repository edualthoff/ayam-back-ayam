package br.flower.boot.auth.session.client;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientSessionDetailsRepository extends JpaRepository<ClientSessionDetails, UUID>{

}
