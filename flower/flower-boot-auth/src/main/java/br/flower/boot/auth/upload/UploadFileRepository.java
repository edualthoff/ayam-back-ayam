package br.flower.boot.auth.upload;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadFileRepository extends CrudRepository<UploadFile, Long> {

	UploadFile findById(long id);
}
