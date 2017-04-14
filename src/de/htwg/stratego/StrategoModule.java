package de.htwg.stratego;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import de.htwg.stratego.controller.IMultiDeviceStrategoController;
import de.htwg.stratego.controller.ISingelDeviceStrategoController;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IPlayerFactory;
import de.htwg.stratego.persistence.IDao;

public class StrategoModule extends AbstractModule {
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
	
	@Override
	protected void configure() {
		bind(ISingelDeviceStrategoController.class)
		.to(de.htwg.stratego.controller.impl.SingleDeviceStrategoController.class);

		bind(IMultiDeviceStrategoController.class)
				.to(de.htwg.stratego.controller.impl.MultiDeviceStrategoController.class);
		
		// IField
		bind(Integer.class)
		.annotatedWith(Names.named("fieldWidth"))
		.toInstance(WIDTH);
		
		bind(Integer.class)
		.annotatedWith(Names.named("fieldHeight"))
		.toInstance(HEIGHT);
		
		bind(IField.class)
		.to(de.htwg.stratego.model.impl.Field.class);
		
		// IPlayerFactory
		bind(IPlayerFactory.class)
		.to(de.htwg.stratego.model.impl.PlayerFactory.class);

		// IDao
		bind(IDao.class)
		.to(de.htwg.stratego.persistence.couchdb.CouchdbDao.class);
	}
	
}
