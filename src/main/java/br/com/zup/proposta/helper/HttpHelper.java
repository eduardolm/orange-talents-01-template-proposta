package br.com.zup.proposta.helper;

import br.com.zup.proposta.model.BiometryImage;
import br.com.zup.proposta.model.Wallet;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class HttpHelper {

    public List<String> getRemoteInfo(HttpServletRequest request) {
        List<String> remoteInfoList = new ArrayList<>();
        remoteInfoList.add(request.getHeader("User-Agent"));
        remoteInfoList.add(request.getRemoteAddr());

        return remoteInfoList;
    }

    public <T> URI buildUri(String id, UriComponentsBuilder uriBuilder, T item) {
        if (item.getClass().equals(BiometryImage.class)) {
            return uriBuilder
                    .path("/api/cartoes/{id}/images/{imageId}")
                    .buildAndExpand(id, ((BiometryImage) item).getId()).toUri();
        }
        if (item.getClass().equals(Wallet.class)) {
            return uriBuilder
                    .path("/api/cartoes/{id}/carteiras/{walletId}")
                    .buildAndExpand(id, ((Wallet) item).getId()).toUri();
        }
        return null;
    }
}
