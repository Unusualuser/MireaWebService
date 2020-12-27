package ru.mirea.intro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.intro.dao.BookDao;
import ru.mirea.intro.dao.RequestDAO;
import ru.mirea.intro.dao.repository.BookRepository;
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
    @Autowired
    BookRepository bookRepository;

    @Override
    public Request testServiceGetMethod(Long id) throws NoSuchRequest {
        Optional<RequestDAO> requestDAO = requestRepository.findById(id);
        if (requestDAO.isPresent()) {
            requestDAO.get().setBookDaoList(bookRepository.findByRequestDaoOrderByIdDesc(requestDAO.get()));
            return RequestMapper.REQUEST_MAPPER.requestDAOToRequest(requestDAO.get());
        }
        throw new NoSuchRequest();
    }

    @Override
    public Request testServicePostMethod(Request request) {
        RequestDAO requestDAO = RequestMapper.REQUEST_MAPPER.requestToRequestDAO(request);
        for (BookDao bookDao : requestDAO.getBookDaoList()) {
            bookDao.setRequestDao(requestDAO);
        }
        RequestDAO outDao = requestRepository.save(requestDAO);
        outDao.setBookDaoList(bookRepository.findByRequestDaoOrderByIdDesc(outDao));
        return RequestMapper.REQUEST_MAPPER.requestDAOToRequest(outDao);
    }

    @Override
    public Request testServicePutMethod(Request request) throws NoSuchRequest {
        if (requestRepository.existsById(request.getId())) {
            RequestDAO requestDAO = RequestMapper.REQUEST_MAPPER.requestToRequestDAO(request);
            for (BookDao bookDao : requestDAO.getBookDaoList()) {
                bookDao.setRequestDao(requestDAO);
            }
            RequestDAO outDao = requestRepository.save(requestDAO);
            outDao.setBookDaoList(bookRepository.findByRequestDaoOrderByIdDesc(outDao));
            return RequestMapper.REQUEST_MAPPER.requestDAOToRequest(outDao);
        }
        throw new NoSuchRequest();
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