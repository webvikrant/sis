package in.co.itlabs.sis.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import in.co.itlabs.sis.business.entities.MediaFile;
import in.co.itlabs.sis.business.entities.MediaFile.Label;

@Service
public class MediaService {

	private DatabaseService databaseService;

	@Autowired
	public MediaService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	// create
	public int createMediaFile(MediaFile mediaFile) {
		int id = 0;
		Sql2o sql2o = databaseService.getSql2o();
		String sql = "insert into media_file (studentId, label, fileName, fileMime, fileBytes, createdAt)"
				+ " values(:studentId, :label, :fileName, :fileMime, :fileBytes, :createdAt)";

		try (Connection con = sql2o.open()) {
			id = con.createQuery(sql).addParameter("studentId", mediaFile.getStudentId())
					.addParameter("label", mediaFile.getLabel()).addParameter("fileName", mediaFile.getFileName())
					.addParameter("fileMime", mediaFile.getFileMime())
					.addParameter("fileBytes", mediaFile.getFileBytes())
					.addParameter("createdAt", mediaFile.getCreatedAt()).executeUpdate().getKey(Integer.class);

			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return id;
	}

	public List<MediaFile> getMediaFiles(int studentId) {
		List<MediaFile> mediaFiles = null;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "select * from media_file where studentId = :studentId order by createdAt desc";

		try (Connection con = sql2o.open()) {
			mediaFiles = con.createQuery(sql).addParameter("studentId", studentId).executeAndFetch(MediaFile.class);
			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mediaFiles;
	}

	public MediaFile getMediaFile(int id) {
		MediaFile mediaFile = null;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "select * from media_file where id = :id";

		try (Connection con = sql2o.open()) {
			mediaFile = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(MediaFile.class);
			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mediaFile;
	}

}