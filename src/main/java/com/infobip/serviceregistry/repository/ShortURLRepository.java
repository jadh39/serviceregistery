package com.infobip.serviceregistry.repository;

import com.infobip.serviceregistry.model.ShortURL;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by prashant on 1/5/17.
 */
public interface ShortURLRepository extends CrudRepository<ShortURL,String> {

       ShortURL findByUrl(String url);
}
