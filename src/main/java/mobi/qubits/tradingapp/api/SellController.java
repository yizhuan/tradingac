package mobi.qubits.tradingapp.api;

import java.util.List;

import javax.validation.Valid;

import mobi.qubits.tradingapp.api.requests.SellRequest;
import mobi.qubits.tradingapp.domain.trader.commands.SellCommand;
import mobi.qubits.tradingapp.query.trade.AssetEntity;
import mobi.qubits.tradingapp.query.trade.AssetEntityRepository;
import mobi.qubits.tradingapp.query.trade.SellEntity;
import mobi.qubits.tradingapp.query.trade.SellEntityRepository;
import mobi.qubits.tradingapp.query.trade.TradeEntity;
import mobi.qubits.tradingapp.query.trade.TradeEntityRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author yizhuan
 *
 */
@RestController
public class SellController{
	
	@Autowired
	private TradeEntityRepository tradeEntryRepository;


	@Autowired
	private SellEntityRepository sellRepo;
	
	
	@Autowired
	private AssetEntityRepository assetRepo;
	

	@Autowired
	private CommandGateway cmdGateway;
	
	@RequestMapping(value = "/api/traders/{id}/sell", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void sell1(@RequestBody @Valid SellRequest request, @PathVariable String id) {		
		AssetEntity a = assetRepo.findByTraderIdAndSymbol(id,  request.getSymbol());		
		cmdGateway.send(new SellCommand(id, request.getSymbol(), request
				.getShares(), request.getPrice(), 
				request.getCostPerShare()==null?a.getCostPerShare() : request.getCostPerShare()));		
	}	
	
	@RequestMapping(value = "/api/traders/{id}/trades/{tradeId}/sell", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void sell2(@RequestBody @Valid SellRequest request, @PathVariable String id,  @PathVariable String tradeId) {
				
		TradeEntity buyEntry = tradeEntryRepository.findOne( tradeId );
		
		cmdGateway.send(new SellCommand(id, request.getSymbol(), request
				.getShares(), request.getPrice(), buyEntry.getPrice()));
	}	
	
	
	@RequestMapping(value = "/api/traders/{id}/trades/{symbol}/sells", method = RequestMethod.GET)
	public @ResponseBody List<SellEntity> findSells(@PathVariable String id, @PathVariable String symbol) {
		
		List<SellEntity> sells = sellRepo.findByTraderIdAndSymbol(id, symbol);
		
		return sells;
	}

	
}
