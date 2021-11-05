package br.flower.boot.auth.upload;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadFileService implements Serializable{
	private static final long serialVersionUID = -3195955706036429651L;
	//private static final Logger log = LogManager.getLogger(UploadFileService.class);

	@Autowired
	private UploadFileRepository upFileDao;
	
	public UploadFile save(UploadFile file) {
    	return this.upFileDao.save(file);
    }
    
	public UploadFile getFile(long fileId) {
        return this.upFileDao.findById(fileId);
    }
}

