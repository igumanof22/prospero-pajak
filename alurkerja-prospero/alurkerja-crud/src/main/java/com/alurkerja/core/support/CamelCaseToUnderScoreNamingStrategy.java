package com.alurkerja.core.support;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * @author muhammaddhitoprihardhanto
 * created on 11/03/21 10.36
 */
public class CamelCaseToUnderScoreNamingStrategy extends PhysicalNamingStrategyStandardImpl {

	public static final CamelCaseToUnderScoreNamingStrategy INSTANCE =
			new CamelCaseToUnderScoreNamingStrategy();

	public static final String CAMEL_CASE_REGEX = "([a-z]+)([A-Z]+)";

	public static final String SNAKE_CASE_PATTERN = "$1\\_$2";

	@Override
	public Identifier toPhysicalCatalogName(
			Identifier name,
			JdbcEnvironment context) {
		return formatIdentifier(
				super.toPhysicalCatalogName(name, context)
		);
	}

	@Override
	public Identifier toPhysicalSchemaName(
			Identifier name,
			JdbcEnvironment context) {
		return formatIdentifier(
				super.toPhysicalSchemaName(name, context)
		);
	}

	@Override
	public Identifier toPhysicalTableName(
			Identifier name,
			JdbcEnvironment context) {
		return formatIdentifier(
				super.toPhysicalTableName(name, context)
		);
	}

	@Override
	public Identifier toPhysicalSequenceName(
			Identifier name,
			JdbcEnvironment context) {
		return formatIdentifier(
				super.toPhysicalSequenceName(name, context)
		);
	}

	@Override
	public Identifier toPhysicalColumnName(
			Identifier name,
			JdbcEnvironment context) {
		return formatIdentifier(
				super.toPhysicalColumnName(name, context)
		);
	}

	private Identifier formatIdentifier(
			Identifier identifier) {
		if (identifier != null) {
			String name = identifier.getText();

			String formattedName = name
					.replaceAll(
							CAMEL_CASE_REGEX,
							SNAKE_CASE_PATTERN)
					.toLowerCase();

			return !formattedName.equals(name) ?
					Identifier.toIdentifier(
							formattedName,
							identifier.isQuoted()
					) :
					identifier;
		} else {
			return null;
		}

	}
}