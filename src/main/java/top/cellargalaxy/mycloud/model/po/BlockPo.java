package top.cellargalaxy.mycloud.model.po;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/7/3
 */
public class BlockPo implements Serializable {
	private static final long serialVersionUID = 4217431251828547844L;
	private int blockId;
	private byte[] block;

	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	public byte[] getBlock() {
		return block;
	}

	public void setBlock(byte[] block) {
		this.block = block;
	}

	@Override
	public String toString() {
		return "BlockPo{" +
				"blockId=" + blockId +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BlockPo blockPo = (BlockPo) o;
		return blockId == blockPo.blockId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(blockId);
	}
}
