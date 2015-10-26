package mobi.qubits.tradingapp.api;

import mobi.qubits.tradingapp.query.AssetEntityRepository;
import mobi.qubits.tradingapp.query.QuoteEntityRepository;
import mobi.qubits.tradingapp.query.SellEntityRepository;
import mobi.qubits.tradingapp.query.TradeEntityRepository;
import mobi.qubits.tradingapp.query.TraderEntityRepository;

import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ReplayController {

	@Autowired
	ReplayingCluster replayingCluster;


	@Autowired
	private TraderEntityRepository traderRepo;

	@Autowired
	private TradeEntityRepository tradeRepo;

	@Autowired
	private AssetEntityRepository assetRepo;

	@Autowired
	private SellEntityRepository sellRepo;

	@Autowired
	private QuoteEntityRepository quoteRepo;


	@RequestMapping(value="/admin/replay", method=RequestMethod.POST)
	void doReplay() {
		replayingCluster.startReplay();
	}


	@RequestMapping(value="/admin/cleardbs", method=RequestMethod.POST)
	private void cleanQueryDBs(){
		traderRepo.deleteAll();
		tradeRepo.deleteAll();
		assetRepo.deleteAll();
		sellRepo.deleteAll();
		quoteRepo.deleteAll();
	}
}
