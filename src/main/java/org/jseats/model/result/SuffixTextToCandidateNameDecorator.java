package org.jseats.model.result;

import javax.xml.bind.annotation.XmlRootElement;

import org.jseats.model.Result;
import org.jseats.model.ResultDecorator;
import org.jseats.model.Seat;

@XmlRootElement
public class SuffixTextToCandidateNameDecorator implements ResultDecorator {

	String text;

	public SuffixTextToCandidateNameDecorator(String text) {
		this.text = text;
	}

	@Override
	public Result decorate(Result result) {

		for (Seat seat : result.getSeats())
			seat.getCandidate().setName(text + seat.getName());

		return result;
	}

}
