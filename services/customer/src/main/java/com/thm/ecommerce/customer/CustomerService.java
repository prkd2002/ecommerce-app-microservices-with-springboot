package com.thm.ecommerce.customer;

import com.thm.ecommerce.exception.CustomerNotFoundException;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {


    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    public String createCustomer(@Valid CustomerRequest customerRequest) {
        var customer = customerRepository.save(customerMapper.toCustomer(customerRequest));
        return customer.getId();

    }

    public void updateCustomer(@Valid CustomerRequest customerRequest) {
        var customer = customerRepository.findById(customerRequest.id())
                .orElseThrow(() -> new CustomerNotFoundException("Customer with " + customerRequest.id() + " was not found !"));

        mergerCustomer(customer, customerRequest);
    }

    private void mergerCustomer(Customer customer, @Valid CustomerRequest customerRequest) {
        if(StringUtils.isNotBlank(customerRequest.firstName())){
            customer.setFirstName(customerRequest.firstName());
        }

        if(StringUtils.isNotBlank(customerRequest.lastName())){
            customer.setLastName(customerRequest.lastName());
        }

        if(StringUtils.isNotBlank(customerRequest.email())){
            customer.setEmail(customerRequest.email());
        }

        if(customerRequest.address() != null){
            customer.setAddress(customerRequest.address());
        }

    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .collect(Collectors.toList());
        }

    public Boolean existsById(String customerId) {
        return customerRepository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with " + customerId + " was not found !"));
    }

    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
