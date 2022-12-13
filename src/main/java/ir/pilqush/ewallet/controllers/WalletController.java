package ir.pilqush.ewallet.controllers;


import ir.pilqush.ewallet.dto.WalletRequest;
import ir.pilqush.ewallet.dto.WalletTransactionRequest;
import ir.pilqush.ewallet.facade.WalletServiceFacade;

import javax.validation.Valid;
import javax.ws.rs.*;

import javax.ws.rs.core.Response;
import java.net.URI;

import java.util.concurrent.CompletionStage;

@Path("/wallet")
public class WalletController {

    private static final String WALLET_URI_FORMAT = "http://localhost:8082/wallet/%s";

    public WalletController(WalletServiceFacade walletServiceFacade) {
        this.walletServiceFacade = walletServiceFacade;
    }

    private final WalletServiceFacade walletServiceFacade;

    @GET
    @Path("/balance")

    public void getBalance(@HeaderParam("walletId") final long walletId) {

    }

    @POST

    public CompletionStage<Response> createWallet(@Valid WalletRequest walletRequest) {
        return walletServiceFacade.createWallet(walletRequest)
                .thenApply(res -> Response.created(getLocation(res.getWalletId())).entity(res).build());
    }

    @POST
    @Path("/transaction")

    public CompletionStage<Response> moneyTransaction(
            @Valid WalletTransactionRequest walletTransactionRequest,
            @HeaderParam("walletId") final long walletId) {
        return walletServiceFacade.transaction(walletId, walletTransactionRequest)
                .thenApply(res -> Response.accepted(res).build());
    }

    private URI getLocation(long walletId) {
        return URI.create(String.format(WALLET_URI_FORMAT, walletId));
    }
}