package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.beans.redirect.Redirect;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.repository.RedirectRepository;
import com.aranya.kayacms.service.RedirectService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RedirectServiceImpl implements RedirectService {

  private final RedirectRepository redirectRepository;

  @Override
  public Redirect getRedirect(WebSite webSite, String fromPath) throws KayaServiceException {
    try {
      Redirect redirect = Redirect.builder().fromPath(fromPath).webSite(webSite).build();
      Example<Redirect> example = Example.of(redirect);
      Optional<Redirect> results = redirectRepository.findOne(example);
      if (results.isPresent()) {
        return results.get();
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public Redirect getRedirect(Long redirectId) throws KayaServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Redirect createRedirect(Redirect redirect) throws KayaServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Redirect updateRedirect(Redirect redirect) throws KayaServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void deleteRedirect(Long redirectId) throws KayaServiceException {
    // TODO Auto-generated method stub
  }
}
