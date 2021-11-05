package br.flower.boot.auth.socialmedia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.flower.boot.auth.socialmedia.SocialMediaAuth.SocialMediaAuthId;

@Repository
public interface SocialMediaAuthRepository extends JpaRepository<SocialMediaAuth, SocialMediaAuthId>{

}
