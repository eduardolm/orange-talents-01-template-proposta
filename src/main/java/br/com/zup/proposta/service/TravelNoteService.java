package br.com.zup.proposta.service;

import br.com.zup.proposta.controller.request.TravelNoteRequestDto;
import br.com.zup.proposta.controller.response.TravelNoteResponseDto;
import br.com.zup.proposta.helper.HttpHelper;
import br.com.zup.proposta.model.CreditCard;
import br.com.zup.proposta.model.TravelNote;
import br.com.zup.proposta.repository.TravelNoteRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TravelNoteService {

    @Autowired
    private HttpHelper httpHelper;

    @Autowired
    private TravelNoteRepository travelNoteRepository;

    @Autowired
    private FetchCreditCard fetchCreditCard;

    public CreditCard addTravelNote(CreditCard creditCard, TravelNoteRequestDto travelNoteRequestDto,
                                    HttpServletRequest request) {

        List<String> remoteProperties = httpHelper.getRemoteInfo(request);
        TravelNote travelNote = new TravelNote(travelNoteRequestDto, creditCard, remoteProperties);
        if (travelNote.equals(travelNoteRepository.findByCreditCard_Id(creditCard.getId()))) {
            return null;
        }

        try {
            TravelNoteResponseDto responseDto = fetchCreditCard.communicateTravelNote(creditCard.getId(),
                    travelNoteRequestDto);

            if (responseDto.getResultado().equals("CRIADO")) {
                travelNoteRepository.save(travelNote);
                creditCard.addTravelNote(travelNote);
                return creditCard;
            }
        }
        catch (FeignException.UnprocessableEntity ex) {
            return null;
        }
        return null;
    }
}
