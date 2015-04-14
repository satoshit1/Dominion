package org.jseats.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Result {

	public enum ResultType {

		// TODO AGB missing documentation
		SINGLE("single-result"), MULTIPLE("multiple-result"), TIE("tie"), UNDECIDED(
				"undecided"), NO_CANDIDATES_INPUTTED("no-candidates-inputted"), CANDIDATES_NO_VOTES("no-candidate-with-votes");

		@SuppressWarnings("unused")
		private final String type;

		ResultType(String name) {
			this.type = name;
		}
	}

	@XmlAttribute
	ResultType type;

	static Logger log = LoggerFactory.getLogger(Result.class);

	static JAXBContext jc;
	static Marshaller marshaller;
	static Unmarshaller unmarshaller;

	@XmlElementWrapper(name = "seats")
	@XmlElement(name = "seat")
	List<Seat> seats;

	public Result() {
		seats = new ArrayList<Seat>();
	}

	public Result(ResultType type) {
		this.type = type;

		seats = new ArrayList<Seat>();
	}

	public ResultType getType() {
		return type;
	}

	protected void setType(ResultType type) {
		this.type = type;
	}

	public int getNumerOfSeats() {
		return seats.size();
	}
	
	public int getNumberOfSeatsForCandidate(String candidate) {

		int count = 0;
		for(Seat innerCandidate : seats)
		{
			if(innerCandidate.getCandidate().getName().contentEquals(candidate))
				count++;
				
		}
		return count;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public Candidate getSeatAt(int position) {
		return seats.get(position).getCandidate();
	}

	public void addSeat(Candidate candidate) {
		Seat seat = new Seat(candidate,this.getNumerOfSeats());
		this.seats.add(seat);
	}

	public void setSeats(List<Candidate> candidates) {
		for (Candidate candidate : candidates) {
			addSeat(candidate);
		}
	}

	public boolean containsSeatForCandidate(Candidate candidate) {

		for (Seat seat : seats) {
			if (seat.getCandidate().equals(candidate))
				return true;
		}

		return false;
	}

	public boolean containsSeatForCandidate(String candidate) {

		for (Seat seat : seats) {
			if (seat.getCandidate().getName().contentEquals(candidate))
				return true;
		}

		return false;
	}

	@Override
	public String toString() {

		StringBuilder str = new StringBuilder("result(");
		str.append(type);
		str.append("):C=");
		str.append(seats.size());
		str.append("=>");
		for (Seat seat : seats) {
			str.append(seat.getSeatNumber());
			str.append(" - ");
			str.append(seat.getCandidate().toString());
			str.append(",");
		}

		return str.toString();
	}

	public void toXML(OutputStream out) throws JAXBException {

		log.debug("Marshalling " + this + " to " + out);

		if (jc == null)
			jc = JAXBContext.newInstance(Result.class);

		if (marshaller == null) {
			marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		}

		marshaller.marshal(this, out);
	}

	public static Result fromXML(InputStream is) throws JAXBException {

		log.debug("Unmarshalling from " + is);

		if (jc == null)
			jc = JAXBContext.newInstance(Result.class);

		if (unmarshaller == null)
			unmarshaller = jc.createUnmarshaller();

		final Result result = (Result) unmarshaller.unmarshal(is);
		//Enforce seats order
		result.getSeats().sort(Comparator.<Seat>naturalOrder());
		return result;
	}

}
