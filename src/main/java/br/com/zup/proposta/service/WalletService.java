package br.com.zup.proposta.service;

import br.com.zup.proposta.controller.request.WalletRequestDto;
import br.com.zup.proposta.controller.response.WalletResponseDto;
import br.com.zup.proposta.model.CreditCard;
import br.com.zup.proposta.model.Wallet;
import br.com.zup.proposta.repository.WalletRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private FetchCreditCard fetchCreditCard;

    @Autowired
    private WalletRepository walletRepository;

    public CreditCard addDigitalWallet(CreditCard creditCard, WalletRequestDto requestDto) {

        try {

            WalletResponseDto responseDto = fetchCreditCard.addWallet(creditCard.getId(), requestDto);

            if (responseDto.getResultado().equals("ASSOCIADA")) {
                Wallet wallet = new Wallet(responseDto.getId(), requestDto, creditCard);
                walletRepository.save(wallet);
                creditCard.addWallet(wallet);
                return creditCard;
            }
        }
        catch (FeignException.UnprocessableEntity ex) {
            return null;
        }
        return  null;
    }
}
