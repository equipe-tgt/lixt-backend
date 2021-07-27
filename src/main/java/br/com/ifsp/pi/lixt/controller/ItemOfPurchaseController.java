package br.com.ifsp.pi.lixt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api(value = "Controller de items da compras")
@RestController
@RequestMapping("/itemOfPurchase")
@RequiredArgsConstructor
public class ItemOfPurchaseController {

}
