package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IFieldFactory;

public class FieldFactory implements IFieldFactory {

	@Override
	public IField create(int width, int height) {
		return new Field(width, height);
	}

}
