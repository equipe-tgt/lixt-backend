package br.com.ifsp.pi.lixt.utils.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Mapper {
	
	public static <T, O> O map(T value, Function<T, O> function) {
		if(Objects.isNull(value))
			return null;
		return function.apply(value);
	}
	
	public static <F, T> List<T> map(Collection<F> from, Function<F, T> function) {
		return map(from, function, Collectors.toList());
	}

	public static <F, T, L> L map(Collection<F> from, Function<F, T> function, Collector<T, ?, L> collector) {
		if(Objects.isNull(from))
			return null;
		return from.stream().map(function).collect(collector);
	}

}
