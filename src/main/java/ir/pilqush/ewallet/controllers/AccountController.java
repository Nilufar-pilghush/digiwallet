package ir.pilqush.ewallet.controllers;

import ir.pilqush.ewallet.EwalletApplication;
import ir.pilqush.ewallet.dto.AccountDto;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

@RequestMapping("account")
@RestController
public class AccountController {

    @RequestMapping(method = RequestMethod.POST)
    public AccountDto createUsers(@RequestBody AccountDto account) {
        return account;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AccountDto getUser(@PathVariable String id) {
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public AccountDto updateUser(@PathVariable String id, @RequestBody User user) {
        return null;
    }


}