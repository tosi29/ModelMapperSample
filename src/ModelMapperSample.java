import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.var;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Single {
	private String fullname;
	private String common;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Multi {
	private String firstname;
	private String lastname;
	private String common;
}

public class ModelMapperSample {
	
	public void singleToMulti(Single single) {
		// Model mapper configuration
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(new PropertyMap<Single, Multi>() {
			@Override
			protected void configure() {
				using(new Converter<Single, String>(){
					public String convert(MappingContext<Single, String> context) {
						return context.getSource().getFullname().split(" ")[0];
					}
				}).map(source, destination.getFirstname());

				using(new Converter<Single, String>(){
					public String convert(MappingContext<Single, String> context) {
						return context.getSource().getFullname().split(" ")[1];
					}
				}).map(source, destination.getLastname());
			}
		});
		
		// Mapping and print result
		System.out.println(mapper.map(single, Multi.class));
	}
	
	public void multiToSingle(Multi multi) {
		// Model mapper configuration
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(new PropertyMap<Multi, Single>() {
			@Override
			protected void configure() {
				using(new Converter<Multi, String>(){
					public String convert(MappingContext<Multi, String> context) {
						return context.getSource().getFirstname() + " " + context.getSource().getLastname();
					}
				}).map(source, destination.getFullname());
			}
		});
		
		// Mapping and print result
		System.out.println(mapper.map(multi, Single.class));
	}

	public static void main(String[] args) {
		var sample = new ModelMapperSample();
		sample.singleToMulti(new Single("First Last", "common"));
		sample.multiToSingle(new Multi("First", "Last", "common"));
	}
}
