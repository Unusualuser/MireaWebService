package ru.mirea.intro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.intro.dao.BookDao;
import ru.mirea.intro.dao.RequestDAO;
import ru.mirea.intro.dao.repository.RequestRepository;
import ru.mirea.intro.exception.NoSuchRequest;
import ru.mirea.intro.mapper.RequestMapper;
import ru.mirea.intro.service.TestService;
import ru.mirea.intro.service.model.Request;

import java.util.Optional;


@Service
public class TestServiceImpl implements TestService {
    @Autowired
    RequestRepository requestRepository;

    @Override
    public Request testServiceGetMethod(Long id) throws NoSuchRequest {
        Optional<RequestDAO> requestDAO = requestRepository.findById(id);
        if (requestDAO.isPresent()) {
            return RequestMapper.REQUEST_MAPPER.requestDAOToRequest(requestDAO.get());
        }
        throw new NoSuchRequest();
    }

    @Override
    public String testServicePostMethod(Request request) {
        RequestDAO requestDAO = RequestMapper.REQUEST_MAPPER.requestToRequestDAO(request);
        for (BookDao bookDao : requestDAO.getBookDaoList()) {
            bookDao.setRequestDao(requestDAO);
        }
        requestRepository.save(requestDAO);
        return "Successfully inserted row!";
    }

    @Override
    public String testServicePutMethod(Request request) throws NoSuchRequest {
        try {
            RequestDAO requestDAO = RequestMapper.REQUEST_MAPPER.requestToRequestDAO(request);
            if (requestRepository.existsById(request.getId())) {
                for (BookDao bookDao : requestDAO.getBookDaoList()) {
                    bookDao.setRequestDao(requestDAO);
                }
                requestRepository.save(requestDAO);
            }
            return "Row was successfully updated!";
        } catch (Exception e) {
            throw new NoSuchRequest();
        }

    }

    @Override
    public String testServiceDeleteMethod(Long id) throws NoSuchRequest {
        Optional<RequestDAO> requestDAO = requestRepository.findById(id);
        if (requestDAO.isPresent()) {
            requestRepository.deleteById(id);
            return "Row was successfully deleted!";
        }
        throw new NoSuchRequest();
    }
}