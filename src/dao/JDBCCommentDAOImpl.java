package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import model.Comment;




public class JDBCCommentDAOImpl implements CommentDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCCommentDAOImpl.class.getName());

	@Override
	public List<Comment> getAll() {

		if (conn == null) return null;
						
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Comment");
						
			while ( rs.next() ) {
				Comment comment = new Comment();
				comment.setIdc(rs.getLong("idc"));
				comment.setSender(rs.getLong("sender"));
				comment.setReceiver(rs.getLong("receiver"));
				comment.setTimeStamp(rs.getTimestamp("timestamp").toString());
				comment.setText(rs.getString("text"));
							
				commentList.add(comment);
				logger.info("fetching commentList: "+comment.getIdc()+" "+comment.getSender()+" "+comment.getReceiver()+" "
				+comment.getTimeStamp()+" "+comment.getText());
					
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return commentList;
	}

	@Override
	public List<Comment> getAllBySender(long sender) {
		
		if (conn == null) return null;
						
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Comment WHERE sender="+sender);

			while ( rs.next() ) {
				Comment comment = new Comment();
				comment.setIdc(rs.getLong("idc"));
				comment.setSender(rs.getLong("sender"));
				comment.setReceiver(rs.getLong("receiver"));
				comment.setTimeStamp(rs.getTimestamp("timestamp").toString());
				comment.setText(rs.getString("text"));

				commentList.add(comment);
				
				logger.info("fetching commentList by sender("+sender+": "+comment.getIdc()+" "+comment.getSender()+" "+comment.getReceiver()+" "
						+comment.getTimeStamp()+" "+comment.getText());
			
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return commentList;
	}
	
	@Override
	public List<Comment> getAllByReceiver(long receiver) {
		
		if (conn == null) return null;
						
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Comment WHERE receiver ="+receiver);

			while ( rs.next() ) {
				Comment comment = new Comment();
				comment.setIdc(rs.getLong("idc"));
				comment.setSender(rs.getLong("sender"));
				comment.setReceiver(rs.getLong("receiver"));
				comment.setTimeStamp(rs.getTimestamp("timestamp").toString().substring(0, 19));
				comment.setText(rs.getString("text"));
				
				commentList.add(comment);
				
				logger.info("fetching commentList by receiver("+receiver+": "+comment.getIdc()+" "+comment.getSender()+" "+comment.getReceiver()+" "
						+comment.getTimeStamp()+" "+comment.getText());
			
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return commentList;
	}
	
	@Override
	public List<Comment> getAllBySenderReceiver(long sender, long receiver) {
		
		if (conn == null) return null;
						
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Comment WHERE sender="+sender+" AND receiver="+receiver);

			while ( rs.next() ) {
				Comment comment = new Comment();
				comment.setIdc(rs.getLong("idc"));
				comment.setSender(rs.getLong("sender"));
				comment.setReceiver(rs.getLong("receiver"));
				comment.setTimeStamp(rs.getTimestamp("timestamp").toString().substring(0, 19));
				comment.setText(rs.getString("text"));
				
				commentList.add(comment);
				
				logger.info("fetching commentList by sender and receiver("+receiver+": "+comment.getIdc()+" "+comment.getSender()+" "+comment.getReceiver()+" "
						+comment.getTimeStamp()+" "+comment.getText());
			
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return commentList;
	}
	
	
	@Override
	public Comment get(long idc) {
		if (conn == null) return null;
		
		Comment comment = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Comment WHERE idc ="+idc);			 
			if (!rs.next()) return null;
			comment= new Comment();
			comment.setIdc(rs.getLong("idc"));
			comment.setSender(rs.getLong("sender"));
			comment.setReceiver(rs.getLong("receiver"));
			comment.setTimeStamp(rs.getTimestamp("timestamp").toString().substring(0, 19));
			comment.setText(rs.getString("text"));
			
						
			logger.info("fetching comment by idc("+idc+": "+comment.getIdc()+" "+comment.getSender()+" "+comment.getReceiver()+" "
					+comment.getTimeStamp()+" "+comment.getText());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comment;
	}
	
	
	public List<Comment> getAllBySearch(String search) {
		search = search.toUpperCase();
		if (conn == null)
			return null;

		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Comment WHERE UPPER(text) LIKE '%" + search + "%'");

			while (rs.next()) {
				Comment comment = new Comment();
				
				comment.setIdc(rs.getLong("idc"));
				comment.setSender(rs.getLong("sender"));
				comment.setReceiver(rs.getLong("receiver"));
				comment.setTimeStamp(rs.getTimestamp("timestamp").toString().substring(0, 19));
				comment.setText(rs.getString("text"));
				
				commentList.add(comment);
				
				logger.info("fetching commentList by text("+search+": "+comment.getIdc()+" "+comment.getSender()+" "+comment.getReceiver()+" "
						+comment.getTimeStamp()+" "+comment.getText());
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return commentList;
	}
	

	@Override
	public long add(Comment comment) {
		long idc=-1;
		if (conn != null){
			
			comment.setTimeStamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Comment (sender,receiver,timestamp,text) VALUES('"+
									comment.getSender()+"','"+
									comment.getReceiver()+"','"+
									comment.getTimeStamp()+"','"+
									comment.getText()+"')",Statement.RETURN_GENERATED_KEYS);
				ResultSet genKeys = stmt.getGeneratedKeys();
				
				if (genKeys.next())
				    idc = genKeys.getInt(1);		
									
				logger.info("creating Comment:("+idc+": "+comment.getSender()+" "+comment.getReceiver()+" "+comment.getTimeStamp()
				+" "+comment.getText());
			} catch (SQLException e) {
				logger.warning("error al añadir comentario: "+e.toString());
			}
		}
		return idc;
	}

	@Override
	public boolean save(Comment comment) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE Comment SET text='"+
									comment.getText()+"' WHERE idc = "+comment.getIdc());
				logger.info("updating Comment: "+comment.getIdc()+" "+comment.getSender()+" "+comment.getReceiver()
				+" "+comment.getText());
				done= true;
			} catch (SQLException e) {
				logger.warning("error actualizando el mensaje: "+e.toString());
			}
		}
		return done;
	}

	@Override
	public boolean delete(long idc) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM Comment WHERE idc ="+idc);
				logger.info("deleting Comment: "+idc);
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}

	
}
