package br.com.ifsp.pi.lixt.instantiator;

import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.utils.conversion.Operators;

public abstract class UserDtoInstantiator extends Operators {

	private UserDtoInstantiator() {}

	public static UserDto createUser(String name, String username, String email, String password) {
		return UserDto.builder()
				.name(name)
				.username(username)
				.email(email)
				.password(password)
				.build();
	}

	public static UserDto createUser(String username, String password) {
		return UserDto.builder()
				.username(username)
				.password(password)
				.build();
	}

	public static UserDto createUser(String username, String password, boolean globalCommentsChronOrder, boolean olderCommentsFirst) {
		return UserDto.builder()
				.username(username)
				.password(password)
				.globalCommentsChronOrder(globalCommentsChronOrder)
				.olderCommentsFirst(olderCommentsFirst)
				.build();
	}

	public static String createUserDtoJson(UserDto userDto) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(KEY_OPEN)
				.append(createJsonLine("id", userDto.getId())).append(COMMA)
				.append(createJsonLine("globalCommentsChronOrder", userDto.isGlobalCommentsChronOrder())).append(COMMA)
				.append(createJsonLine("olderCommentsFirst", userDto.isOlderCommentsFirst()))
				.append(KEY_CLOSE);

		return stringBuilder.toString();
	}
}
