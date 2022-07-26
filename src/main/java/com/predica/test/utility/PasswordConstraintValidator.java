package com.predica.test.utility;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.AllowedCharacterRule;
import org.passay.CharacterRule;
import org.passay.DictionaryRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

	private DictionaryRule dictionaryRule;

	@Override
	public void initialize(ValidPassword constraintAnnotation) {
		try {
			String invalidPasswordList = this.getClass().getResource("/invalid-password-list.txt").getFile();
			dictionaryRule = new DictionaryRule(new WordListDictionary(WordLists.createFromReader(
					// Reader around the word list file
					new FileReader[] { new FileReader(invalidPasswordList) },
					// True for case sensitivity, false otherwise
					false,
					// Dictionaries must be sorted
					new ArraysSort())));
		} catch (IOException e) {
			throw new RuntimeException("could not load word list", e);
		}
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		PasswordValidator validator = new PasswordValidator(Arrays.asList(

				new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.Digit, 1),
				new CharacterRule(EnglishCharacterData.Special, 1),
//				new AllowedCharacterRule(new char[] {'_','#','$','%','.'}),
				dictionaryRule));

		RuleResult result = validator.validate(new PasswordData(password));

		if (result.isValid()) {
			return true;
		}

		List<String> messages = validator.getMessages(result);
		String messageTemplate = messages.stream().collect(Collectors.joining(","));
		context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation()
				.disableDefaultConstraintViolation();
		return false;
	}
}